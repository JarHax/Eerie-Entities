package com.jarhax.spooky.client;


import com.jarhax.spooky.SpookyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.*;

import java.util.*;

public class ShaderHandler {
    
    
    public static final int VERT = ARBVertexShader.GL_VERTEX_SHADER_ARB;
    public static final int FRAG = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
    public static int WISP;
    
    public static void registerShaders() {
        try {
            WISP = create("/assets/" + SpookyMod.MODID + "/shaders/wisp");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void useShader(int shader, Map<String, Object> data) {
        if(!useShaders())
            return;
        
        ARBShaderObjects.glUseProgramObjectARB(shader);
        
        if(shader != 0) {
            int time = ARBShaderObjects.glGetUniformLocationARB(shader, "time");
            ARBShaderObjects.glUniform1fARB(time, ClientEvents.totalTime);
            
            
            int res = ARBShaderObjects.glGetUniformLocationARB(shader, "resolution");
            ARBShaderObjects.glUniform3iARB(res, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, 0);
            
            for(Map.Entry<String, Object> entry : data.entrySet()) {
                int id = ARBShaderObjects.glGetUniformLocationARB(shader, entry.getKey());
                if(entry.getValue() instanceof Float) {
                    ARBShaderObjects.glUniform1fARB(id, (Float) entry.getValue());
                }
                if(entry.getValue() instanceof Integer) {
                    ARBShaderObjects.glUniform1iARB(id, (Integer) entry.getValue());
                }
            }
            
            
        }
    }
    
    
    public static void userShader(int shader) {
        useShader(shader, new HashMap<>());
    }
    
    public static boolean useShaders() {
        return OpenGlHelper.shadersSupported;
    }
    
    public static void releaseShader() {
        useShader(0, new HashMap<>());
    }
    
    
    public static int create(String location) {
        int fragShader = 0;
        
        int program = 0;
        try {
            fragShader = createShader(location + ".frag", FRAG);
            
        } catch(Exception exc) {
            exc.printStackTrace();
            return 0;
        } finally {
        }
        
        program = ARBShaderObjects.glCreateProgramObjectARB();
        
        if(program == 0)
            return 0;
        
        ARBShaderObjects.glAttachObjectARB(program, fragShader);
        
        ARBShaderObjects.glLinkProgramARB(program);
        if(ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(program));
            return 0;
        }
        
        ARBShaderObjects.glValidateProgramARB(program);
        if(ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(program));
            return 0;
        }
        
        return program;
    }
    
    private static int createShader(String filename, int shaderType) throws Exception {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
            
            if(shader == 0)
                return 0;
            
            
            ARBShaderObjects.glShaderSourceARB(shader, IOUtils.toString(ShaderHandler.class.getResourceAsStream(filename)));
            ARBShaderObjects.glCompileShaderARB(shader);
            
            if(ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
            
            return shader;
        } catch(Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw exc;
        }
    }
    
    
    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
    
    
}
