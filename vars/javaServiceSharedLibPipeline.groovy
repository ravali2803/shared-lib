final sharedlib = library('sharedlib@master')
def call(Map PipelineParams)

pipeline {
    agent any
    stages {
        stage('Compile') {
            steps {
                echo 'mvn clean install'
            }
        }
        stage('Unit Test') {
            steps {
                echo 'mvn test'
            }
        }
        stage('Code Analysis') {
            steps {
                echo 'sonar analysis'
            }
        }
        stage('Publish to Artifactory') {
            when {
                branch 'master'
            }
            steps {
                echo 'publish to maven repo in artifactory'
            }
        }
