pipeline {
    agent any
    triggers {
        pollSCM('H/5 * * * *')
    }
    stages {
        stage('change detection') {
            steps {
                script {
                    def changedDirs = sh(returnStdout: true, script: 'git diff --name-only HEAD^ HEAD').trim().split("\n")

                    for (dir in changedDirs) {
                        if (fileExists("${dir}/pom.xml")) {
                            echo "Değişiklik tespit edildi: ${dir}"
                            buildDockerImage(dir)
                        }
                    }
                }
            }
        }
    }
}

def buildDockerImage(dir) {
    stage("Docker Image Creation - ${dir}") {
        dir(dir) {
            sh 'mvn compile jib:dockerBuild'
        }
    }
}