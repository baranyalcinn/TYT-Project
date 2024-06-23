def buildDockerImage(String dir) {
    echo "Building Docker image for directory: ${dir}"
    try {
        // Use sh to run the Maven command
        sh "mvn -f ${dir}/pom.xml compile jib:dockerBuild"
        echo "Docker image successfully built for ${dir}"
    } catch (Exception e) {
        echo "Failed to build Docker image for ${dir}: ${e.message}"
    }
}

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
                    // Get the top-level directories of the changed files
                    def changedDirs = changedFiles.collect { it.split('/')[0] }.unique()
                    echo "Top-level changed directories: ${changedDirs.join(', ')}"
                    // Check each top-level directory for pom.xml
                    for (def dir : changedDirs) {
                        def pomPath = "${dir}/pom.xml"
                        if (fileExists(pomPath)) {
                            echo "Change detected in directory with pom.xml: ${dir}"
                            // Call the function directly
                            buildDockerImage(dir as String)
                        } else {
                            echo "No pom.xml found in ${dir}, skipping..."
                        }
                    }
                }
            }
        }
    }
}