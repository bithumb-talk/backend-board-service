pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        SOURCECODE_JENKINS_CREDENTIAL_ID = 'jenkins-github-wh'
        SOURCE_CODE_URL = 'https://github.com/MinjiY/Springboot-jenkins.git'
        RELEASE_BRANCH = 'master'
    }
    stages {
        stage('Init') {
            steps {
                echo 'clear'
                //sh 'docker stop $(docker ps -aq)'
                sh 'docker stop board'
                //sh 'docker rm $(docker ps -aq)'
                sh 'docker rm board'
                deleteDir()
            }
        }
        stage('clone') {
            steps {
                git url: "$SOURCE_CODE_URL",
                    branch: "$RELEASE_BRANCH",
                    credentialsId: "$SOURCECODE_JENKINS_CREDENTIAL_ID"
                sh "ls -al"
            }
        }
        stage('dockerizing') {
            steps {
                //sh "pwd"
                //dir("./backend"){
                    sh "pwd"
                    sh "chmod +x ./gradlew"
                    sh "./gradlew build --stacktrace"
                    //sh "gradle wrapper --stacktrace"
                    //sh "gradle bootJar"

                    sh "docker build -t upi907/testdeploy ."
                //}
            }
        }
        stage('deploy') {
            steps {
                sh '''
                
                  docker run -d -p 9090:8080 --name board upi907/testdeploy
                '''
            }
        }
    }
}
