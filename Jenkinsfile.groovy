pipeline {
    agent any

    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('Detect Changes') {
            steps {
                script {
                    // Get the list of changed files
                    def changedFiles = sh(returnStdout: true, script: 'git diff --name-only HEAD^ HEAD').trim().split("\n")
                    echo "Changed files: ${changedFiles.join(', ')}"

                    def changedDirsWithPom = []
                    for (file in changedFiles) {
                        def currentDir = file.substring(0, file.lastIndexOf('/'))
                        while (currentDir != '') {
                            if (fileExists("${currentDir}/pom.xml")) {
                                echo "Change detected in directory with pom.xml: ${currentDir}"
                                changedDirsWithPom.add(currentDir)
                                break // Stop searching once pom.xml is found
                            }
                            // Go up one directory level
                            currentDir = currentDir.substring(0, currentDir.lastIndexOf('/'))
                            if (currentDir == '/') {
                                break // Reached the root directory
                            }
                        }
                    }
                    changedDirsWithPom = changedDirsWithPom.unique()

                    // Build images for each unique directory containing pom.xml
                    for (dir in changedDirsWithPom) {
                        buildDockerImage(dir)
                    }
                }
            }
        }
    }
}

def buildDockerImage(dir) {
    stage("Build Docker Image - ${dir}") {
        dir(dir) {
            echo "Building Docker image for directory: ${dir}"
            try {
                sh 'mvn compile jib:dockerBuild'
                echo "Docker image successfully built for ${dir}"
            } catch (Exception e) {
                echo "Failed to build Docker image for ${dir}: ${e.message}"
            }
        }
    }
}