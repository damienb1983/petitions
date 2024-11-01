pipeline {
    agent any

    environment {
        MAVEN_HOME = "/usr/share/maven"
        JAVA_HOME = "/usr/lib/jvm/java-11-openjdk-amd64"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/damienb1983/petitions.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "Building the project..."
                    sh "${MAVEN_HOME}/bin/mvn clean compile"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo "Running tests..."
                    sh "${MAVEN_HOME}/bin/mvn test"
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    echo "Packaging the application..."
                    sh "${MAVEN_HOME}/bin/mvn package"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "Deploying the application..."
                    // Add deployment steps here if needed
                }
            }
        }
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}