package com.leo.leoslib.selectPic.model;

import java.io.Serializable;

/**
 * 图片对象
 *
 */
public class ImageItem implements Serializable
{
	private static final long serialVersionUID = -4343224667446878258L;
	public String imageId;
	public String thumbnailPath;
	public String sourcePath;
	public boolean isSelected = false;
}
