final shared-lib = library('shared-lib@master')
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
                echo 'publish to artifactory'
            }
        }
        stage('Dev Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'deploy to Dev env'
            }
        }
        stage('Dev-->QA') {
            when {
                branch 'master'
            }
            steps {
                echo

            }                

    }
}