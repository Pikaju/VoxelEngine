#version 330

uniform sampler2D pixelPositions;
uniform vec3 cameraPosition;

in vec2 vertexTexcoord;

layout (location = 0) out vec4 pixelColor;

float getDistanceToCamera(vec2 texcoord);

void main() {
	float distanceToCamera = getDistanceToCamera(vertexTexcoord);
	vec3 vertexPosition = texture(pixelPositions, vertexTexcoord).xyz;

	float s = 0.05 / distanceToCamera;
	float avarage = 0.0;
	
	const int numNoises = 16;
	const vec2[numNoises] noise = vec2[numNoises](
		(vec2(0.98, -0.7)),
		(vec2(0.21, 0.3)),
		(vec2(-0.7, 0.89)),
		(vec2(-0.3, -0.3)),
		(vec2(0.4, -0.9)),
		(vec2(0.6, 0.5)),
		(vec2(-1.0, -0.6)),
		(vec2(-0.1, 0.2)),
		(vec2(0.18, -0.9)),
		(vec2(0.21, 0.7)),
		(vec2(-0.3, 0.5)),
		(vec2(-0.4, -0.6)),
		(vec2(0.5, -0.4)),
		(vec2(0.6, 0.2)),
		(vec2(-0.7, -0.1)),
		(vec2(-0.8, 0.0))
	);
	
	for (int i = 0; i < numNoises; i++) {
		avarage += clamp(getDistanceToCamera(vertexTexcoord + noise[i] * s) - distanceToCamera, -0.1, 0.1);
	}
	avarage /= numNoises;
	
	pixelColor = vec4(vec3(avarage * 3.0 + 0.5), 1.0); 
}

float getDistanceToCamera(vec2 texcoord) {
	return length(texture(pixelPositions, texcoord).xyz - (cameraPosition));
}