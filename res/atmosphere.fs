#version 330

in vec3 vertexPosition;

layout (location = 0) out vec4 pixelColor;

void main() {
	vec3 normalizedPosition = normalize(vertexPosition);
	vec3 sunPosition = normalize(vec3(-1, 2, 0.5));
	float distanceToSun = length(normalizedPosition - sunPosition);
	distanceToSun = min(1, 0.02 / pow(distanceToSun, 3));
	
	pixelColor = vec4(mix(vec3(0.0, 0.5, 1.0), vec3(1.0, 1.0, 1.0), distanceToSun), 1.0);
}