#version 330
layout(location=0) in vec4 vertex;
layout(location=1) in vec2 uvkoordinaten;
layout(location=2) in vec3 normals;

//Matrix aus Java
uniform mat4 rotmat2;
uniform mat4 persmat;

out vec2 vuv;
out vec3 normal;
out vec3 vertexRoom;

void main() {
    vertexRoom = vec3(rotmat2*vertex);

	//vcolor = farbenAusJava;
	vuv =  uvkoordinaten;
    normal = normals;
    //letzte Koordinate 1 dann punkt (immer in glsl)
	//gl_Position = vec4(rot(eckenAusJava, alpha), 0.0, 1.0);
	gl_Position = persmat * rotmat2 * vertex;




}