pipeline {
    agent {
        docker {
            image 'maven:3.6.3-jdk-8'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    environment {
        GIT_REPO = 'https://github.com/baranyalcinn/TYT-Project.git'
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    def changes = getChangedDirectories()
                    echo "Changed directories: ${changes}"
                    if (changes.size() == 0) {
                        currentBuild.result = 'SUCCESS'
                        return
                    }
                }
                checkout scm
            }
        }
        stage('Build and Push Docker Images') {
            steps {
                script {
                    def changes = getChangedDirectories()
                    for (dir in changes) {
                        dir = dir.replaceAll('^TYT-Project/', '')
                        echo "Building Docker image for ${dir}"
                        dir("${env.WORKSPACE}/TYT-Project/${dir}") {
                            sh 'mvn compile jib:dockerBuild'
                        }
                    }
                }
            }
        }
    }
}

def getChangedDirectories() {
    def changedFiles = sh(
        script: "git diff-tree --no-commit-id --name-only -r \$(git log -1 --pretty=format:'%H')",
        returnStdout: true
    ).trim().split("\n")
    def directories = [] as Set
