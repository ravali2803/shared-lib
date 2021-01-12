<<<<<<< HEAD
final sharedlib = library('sharedlib@master')
=======
final shared-lib = library('shared-lib@master')
>>>>>>> 16af0a42c25a9ff7fce76856de7bfa868142fccc
def call(Map PipelineParams)

pipeline {
    agent any
    stages {
        stage('Compile') {
            steps {
                echo 'npm build'
            }
        }
        stage('Unit Test') {
            steps {
                echo 'npm test'
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
                echo 'publish to npm module repo in artifactory'
            }
        }
