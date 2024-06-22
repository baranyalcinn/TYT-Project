pipeline {
    agent any

    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('Detect Changes') {
            steps {
                script {
                    def changedDirs = sh(returnStdout: true, script: 'git diff --name-only HEAD^ HEAD').trim().split("\n")
                    echo "Changed directories: ${changedDirs.join(', ')}"

                    for (dir in changedDirs) {
                        if (fileExists("${dir}/pom.xml")) {
                            echo "Change detected in directory with pom.xml: ${dir}"
                            buildDockerImage(dir)
                        } else {
                            echo "No pom.xml found in ${dir}, skipping..."
                        }
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
