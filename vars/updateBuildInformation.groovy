//
// Custom step: Update build name and build description with relevant build data
// Parameters:
//		assimilateVersionIntoBuildName:	Indicates whether to add the version name into the build name
//			default:								true
//
def call(boolean assimilateVersionIntoBuildName = true) {
	def primeVersion = sh(script: "./gradlew -Preckon.scope=${env.JENKINS_SLAVE_K8S_RECKON_SCOPE} -Preckon.stage=${env.JENKINS_SLAVE_K8S_RECKON_STAGE} -Pdemo4echo.designatedTagName=${params.DESIGNATED_VERSION} -Pdemo4echo.designatedTagMessage='${params.DESIGNATED_VERSION_MESSAGE}' printApplicableVersion | grep Prime | awk '{print \$3}'", returnStdout: true)
	def adjustedBuildVersion = primeVersion && assimilateVersionIntoBuildName == true ? "|v${primeVersion}" : ""
	def k8sJenkinsSlaveNodeName = sh(script: 'echo $NODE_HOST_NAME_ENV_VAR', returnStdout: true)

	buildName "#${env.BUILD_NUMBER}${adjustedBuildVersion}"
	buildDescription "${JOB_NAME}@${k8sJenkinsSlaveNodeName}"
}
