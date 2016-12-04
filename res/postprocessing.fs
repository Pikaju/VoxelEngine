#version 330

uniform sampler2D pixelColors;
uniform sampler2D pixelPositions;
uniform sampler2D pixelOcclusion;
uniform sampler2D pixelAtmosphere;
uniform float fogDistance;

uniform vec3 cameraPosition;

varying vec2 vertexTexcoord;

layout (location = 0) out vec4 pixelColor;

void main() {
	
	float distanceToCamera = length(texture(pixelPositions, vertexTexcoord).xyz - cameraPosition);
	
	float occlusion = texture(pixelOcclusion, vertexTexcoord).r;
	
	float fog = pow(clamp(distanceToCamera / fogDistance, 0.0, 1.0), 4.0);
	vec3 fogColor = texture(pixelAtmosphere, vertexTexcoord).rgb;
	
	pixelColor = vec4(mix(occlusion * texture(pixelColors, vertexTexcoord).rgb, fogColor, fog), 1.0);
}
