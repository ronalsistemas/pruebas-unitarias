pipeline {
   agent {
       docker {
           image 'maven:3.8.8-eclipse-temurin-17-alpine'
       }
   }
//     triggers {
// //         cron('* * * * *') // Ejecutar cada minuto
// //         cron('*/2 * * * *') // Ejecutar cada 2 minutos
//     }
   stages {
       stage('Compile') {
           steps {
               sh 'mvn clean compile -B -ntp'
           }
       }
       stage('Test') {
           steps {
               sh 'mvn test -Dtest=MascotaService05JUnitMockitoCoverageTest -B -ntp'
//                 sh 'mvn test -Dtest=MascotaService05JUnitMockitoCoverageTest -Dmaven.test.failure.ignore=true -B -ntp'
               junit 'target/surefire-reports/*.xml'
//                 jacoco()



//                 recordCoverage(tools: [[parser: 'JACOCO']])


               discoverReferenceBuild()
               recordCoverage(tools: [[parser: 'JACOCO']])


//                 recordCoverage(
//                     tools: [[parser: 'JACOCO']],
//                     sourceCodeRetention: 'EVERY_BUILD',
//                     qualityGates: [
//                         [threshold: 30.0, metric: 'LINE'],
//                         [threshold: 30.0, metric: 'BRANCH']
//                     ]
//                 )



//                 recordCoverage(
//                     tools: [[parser: 'JACOCO']],
//                     sourceCodeRetention: 'EVERY_BUILD',
//                     qualityGates: [
//                             [threshold: 30.0, metric: 'LINE', criticality: 'FAILURE'],
//                             [threshold: 30.0, metric: 'BRANCH', criticality: 'FAILURE']
//                     ]
//                 )



//                 discoverReferenceBuild()
//                 recordCoverage(
//                     tools: [[parser: 'JACOCO']],
//                     sourceCodeRetention: 'EVERY_BUILD',
//                     qualityGates: [
//                         [threshold: 20.0, metric: 'LINE', baseline: 'PROJECT'],
//                         [threshold: 20.0, metric: 'BRANCH', baseline: 'PROJECT']
//                     ]
//                 )


           }
       }
       stage('Package') {
           steps {
               sh 'mvn package -DskipTests -B -ntp'
           }
       }
       stage('SonarQube') {
           steps {
               withSonarQubeEnv('sonarqube'){
                   sh 'mvn sonar:sonar -B -ntp'
               }
           }
       }
       stage("Quality Gate"){
           steps{
               timeout(time: 1, unit: 'MINUTES') {
                   waitForQualityGate abortPipeline: true
               }
           }
       }
   }
   post{
       always{
           cleanWs()
       }
   }
}