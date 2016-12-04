#version 330

uniform vec3 cameraPosition;

in vec3 vertexPosition;
in vec3 vertexNormal;
in vec4 vertexColor;

layout (location = 0) out vec4 pixelColor;
layout (location = 1) out vec4 pixelPosition;

void main() {
	float distanceToCamera = length(vertexPosition - cameraPosition);
	float lightColor = clamp((dot(normalize(vec3(0.7, -1.0, 0.0)), vertexNormal) * 0.4 + 0.6), 0.0, 1.0);
	
	vec3 surfaceColor = vertexColor.rgb * lightColor;
	pixelColor = vec4(surfaceColor, 1.0);
	pixelPosition = vec4(vertexPosition, 1.0);
}