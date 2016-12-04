#version 130

out vec3 vertexPosition;
out vec3 vertexNormal;
out vec4 vertexColor;

void main() {
	vertexPosition = gl_Vertex.xyz;
	vertexNormal = gl_Normal;
	vertexColor = gl_Color;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}