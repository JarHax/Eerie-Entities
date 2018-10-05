#version 120

uniform sampler2D bgl_RenderedTexture;
uniform sampler2D image;
uniform float time;

uniform vec3 resolution;

void main() {
    vec2 texcoord = vec2(gl_TexCoord[0]);


    vec4 color = texture2D(bgl_RenderedTexture, texcoord);

    float r = (color.r * gl_Color.r);
    float g = (color.g * gl_Color.g);
    float b = (color.b * gl_Color.b);
    float a = (color.a * gl_Color.a);

    if(r < 0.2 && g < 0.2 && b < 0.2){
        a = 0;
    }


    gl_FragColor = vec4(r, g, b, a);
}