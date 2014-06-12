package com.decker.essentiallib.IO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioFormat.Encoding;

import org.omg.CORBA.SystemException;

public class StaticFileReader
{
    private String pluginName;
    private Pattern urlPattern;

    public StaticFileReader(String pluginName)
    {
	this.pluginName = pluginName;
	this.urlPattern = Pattern.compile(String.format("(?<=%s/).*", this.getPluginName()));
    }

    public String getPluginName()
    {
	return this.pluginName;
    }

    public byte[] readBytes(String url) throws FileNotFoundException,IOException
    {
	Matcher matcher=urlPattern.matcher(url);
	if(!matcher.find())
	{
	    throw new FileNotFoundException("Cant find file by this url");
	}
	String filePath=matcher.group(0);
	InputStream is = this.getClass().getResourceAsStream(String.format("/%s/%s",this.getPluginName(), filePath));
	return sun.misc.IOUtils.readFully(is, -1, true);
	
    }

    public String readString(String url) throws FileNotFoundException,IOException
    {
	return new String (this.readBytes(url));
	
    }

}
