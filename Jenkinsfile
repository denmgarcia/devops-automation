pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages{
        stage("Build Maven") {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/denmgarcia/devops-automation']])
                sh 'mvn clean install'
            }
        }
        stage("Build Docker images") {
            steps {
               script {
                   sh 'docker build -t cyborden/devops-integration .'
               }
            }
        }
        stage("Pushing Docker images to HUB...") {
            steps {
               script {
                   withCredentials([string(credentialsId: 'dockerhub-pwd2', variable: 'dockerhubpwd')]) {
                       sh 'docker login -u cyborden -p ${dockerhubpwd}'

                       sh 'docker push cyborden/devops-integration'
                   }


               }
            }
        }

    }
}