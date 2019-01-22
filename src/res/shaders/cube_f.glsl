#version 330
in vec2 vuv;
in vec3 normal;
in vec3 vertexRoom;

out vec3 pixelFarbe;

uniform sampler2D smplr;

vec3 light = vec3(2,3,3);
float lintensity = 1.0f;
float mambient = 0.2f;


void main () {
	vec4 texel = texture(smplr, vuv);

	vec3 vertexLight = normalize(light - vertexRoom);
	vec3 r = normalize(2*dot(vertexRoom,normal)*normal-vertexLight);
	vec3 v = normalize(-vertexRoom);

	float mdiffuse = max(0,dot(vertexLight,normal)*lintensity);
    float mspecularity = pow(max(0,lintensity*dot(r,v)),100)*lintensity;
    float lr = mambient+mdiffuse+mspecularity;

	pixelFarbe = vec3(texel)*lr;
}