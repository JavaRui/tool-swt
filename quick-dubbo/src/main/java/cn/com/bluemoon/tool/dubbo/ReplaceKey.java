package cn.com.bluemoon.tool.dubbo;

import java.util.Map;

import com.yt.utils.file.YtFileUtils;

public class ReplaceKey {
	public String artifactParent;
	public String groupId;
	public String name;
	public String artifactId;
	public String registryAddress;
	public String protocalName;
	public String protocolPort;
	public Map<String, String> keyMap;
	
	
	public void trim() {
		groupId = groupId.trim();
		artifactParent = artifactParent.trim();
		name = name.trim();
		artifactId = artifactId.trim();
	}
	
	public String upArtifactId() {
		return YtFileUtils.upFirstName(artifactId);
	}
	
}
