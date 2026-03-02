pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        DOCKER_CMD = "/Applications/Docker.app/Contents/Resources/bin/docker"
        DOCKERHUB_CREDENTIALS_ID = 'DockerHub_ID'
        DOCKERHUB_REPO = '218468/week6_assignment'
        DOCKER_IMAGE_TAG = 'v1'
    }



    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/GamAungLahpai/Teamperature_Converter_Docker.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Generate Report') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Coverage Report') {
            steps {
                recordCoverage(tools: [[pattern: '**/target/site/jacoco/jacoco.xml']])
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '${DOCKER_CMD} build -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG} .'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DockerHub_ID', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        ${DOCKER_CMD} login -u $DOCKER_USER -p $DOCKER_PASS
                        ${DOCKER_CMD} push ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}
                    '''
                }
            }
        }



    }
}