#version 130

out vec3 vertexPosition;

void main() {
	vertexPosition = gl_Vertex.xyz;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}