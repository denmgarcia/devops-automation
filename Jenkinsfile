pipeline {
  agent any
  stages {
    stage('Unit Test') {
      steps {
        sh 'mvn clean test'
      }
    }
    stage('Deploy Standalone') {
      steps {
        sh 'mvn deploy -P standalone'
      }
    }
    stage('Deploy AnyPoint') {
      environment {
        ANYPOINT_CREDENTIALS = credentials('anypoint.credentials')
      }
      steps {
        sh 'mvn deploy -P arm -Darm.target.name=local-4.0.0-ee -Danypoint.username=${ANYPOINT_CREDENTIALS_USR}  -Danypoint.password=${ANYPOINT_CREDENTIALS_PSW}'
      }
    }
    stage('Deploy CloudHub') {
      environment {
        ANYPOINT_CREDENTIALS = credentials('anypoint.credentials')
      }
      steps {
        sh 'mvn deploy -P cloudhub -Dmule.version=4.0.0 -Danypoint.username=${ANYPOINT_CREDENTIALS_USR} -Danypoint.password=${ANYPOINT_CREDENTIALS_PSW}'
      }
    }
  }
}
pipeline {
  agent any
  tools {
    maven 'maven'
  }
  stages {
    stage("Build Maven") {
      steps {
        checkout scmGit(branches: [
          [name: '*/main']
        ], extensions: [], userRemoteConfigs: [
          [url: 'https://github.com/denmgarcia/devops-automation']
        ])
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