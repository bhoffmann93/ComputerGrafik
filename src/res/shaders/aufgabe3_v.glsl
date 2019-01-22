#version 330
layout(location=0) in vec4 eckenAusJava;
layout(location=1) in vec3 farbenAusJava;
layout(location=2) in vec2 uvkoordiaten;
layout(location=3) in vec3 normals;
//Matrix aus Java
uniform mat4 rotmat;
uniform mat4 persmat;

out vec3 vcolor;
out vec2 vuv;
out vec3 normal;
out vec3 vertexRoom;

void main() {
    vertexRoom = vec3(rotmat*eckenAusJava);

	//vcolor = farbenAusJava;
	vuv = uvkoordiaten;
    normal = normals;
    //letzte Koordinate 1 dann punkt (immer in glsl)
	//gl_Position = vec4(rot(eckenAusJava, alpha), 0.0, 1.0);
	gl_Position = persmat * rotmat * eckenAusJava;




}
