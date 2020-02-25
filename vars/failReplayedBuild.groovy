//
// Custom step:	Prevents from replayed builds to exexute,
// 					as otherwise such builds will generate artifacts (like docker images or helm charts)
//						with old/obsolete/stale code 
//
def call(String buildID) {
	echo "Display name is:[${currentBuild.displayName}]"
	echo "Decription is:[${currentBuild.description}]"

	// Check if this is a replay of an old build and if so, stop the build
	if (currentBuild.displayName != null || currentBuild.description != null) {
		error "This is a replay of build [${buildID}] which is not allowed - build is stopped!"
	}
	else {
		echo "Build [${buildID}] is a new build - build is allowed to continue."
	}
}