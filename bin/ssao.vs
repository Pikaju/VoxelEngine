#version 130

out vec2 vertexTexcoord;

void main() {
	vertexTexcoord = gl_MultiTexCoord0.xy;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}