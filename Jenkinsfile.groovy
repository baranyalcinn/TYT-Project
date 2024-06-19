pipeline {
    agent {
        docker {
            image 'jenkins-agent'
        }
    }
    environment {
        GIT_REPO = 'https://github.com/baranyalcinn/TYT-Project.git'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout scm
                    def changes = getChangedDirectories()
                    echo "Changed directories: ${changes}"
                    if (changes.isEmpty()) {
                        echo "No changes detected in TYT-Project subdirectories, skipping build."
                        currentBuild.result = 'SUCCESS'
                        return
                    }
                }
            }
        }

        stage('Verify Docker Installation') {
            steps {
                sh 'docker --version'
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
    for (file in changedFiles) {
        def parts = file.split('/')
        if (parts.length > 1 && parts[0] == 'TYT-Project') {
            directories.add(parts[1])
        }
    }
    return directories as List
}
