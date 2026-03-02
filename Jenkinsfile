pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        PATH = "/Applications/Docker.app/Contents/Resources/bin:${env.PATH}"
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
                sh '/Applications/Docker.app/Contents/Resources/bin/docker build -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG} .'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: '${DOCKERHUB_CREDENTIALS_ID}', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        /Applications/Docker.app/Contents/Resources/bin/docker login -u $DOCKER_USER -p $DOCKER_PASS
                        /Applications/Docker.app/Contents/Resources/bin/docker push ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}
                    '''
                }
            }
        }



    }
}