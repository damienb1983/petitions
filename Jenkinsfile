pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'myapp:latest'
        CONTAINER_NAME = 'myappcontainer'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/damienb1983/petitions.git'
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -f Dockerfile -t ${DOCKER_IMAGE} .'
                }
            }
        }

        stage('Deploy to Docker') {
            steps {
                script {
                    sh 'docker build -f Dockerfile -t ${DOCKER_IMAGE} .'
                    sh 'docker rm -f ${CONTAINER_NAME} || true'
                    sh 'docker run --name "${CONTAINER_NAME}" -p 8081:8080 --detach ${DOCKER_IMAGE}'
                    sh 'docker ps'
                }
            }
        }
    }
}