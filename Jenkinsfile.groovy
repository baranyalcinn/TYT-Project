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

                    for (dir in changedDirs) {
                        if (fileExists("${dir}/pom.xml")) {
                            echo "Change detected: ${dir}"
                            buildDockerImage(dir)
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
            sh 'mvn compile jib:dockerBuild'
        }
    }
}