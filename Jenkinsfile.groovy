pipeline {
    agent any
    triggers {
        scm('* * * * *')
    }
    stages {
        stage('Change Detection') {
            steps {
                script {
                    def changedDirs = sh(returnStdout: true, script: 'git diff --name-only HEAD^ HEAD').trim().split("\n")
                    for (dir in changedDirs) {
                        if (fileExists("${dir}/pom.xml")) {
                            echo "file changed ${dir}"
                            buildDockerImage(dir)
                        }
                    }
                }
            }
        }
    }
}

def buildDockerImage(dir) {
    stage("Docker Image - ${dir}") {
        dir(dir) {
            sh 'mvn compile jib:dockerBuild'
        }
    }
}