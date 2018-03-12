package webhome2;

import java.util.Date;

public class FileDisplay {

	private Integer id;
	private String name;
	private String type; // content type of the uploaded file
	private long size;
	private Date date;
	private Integer parent;
	private boolean isFolder;
	private Integer userId;

	public FileDisplay(Integer id,String name,String type,long size,Date date,Integer parent,boolean isFolder,Integer userId)
	{
		this.id = id;
		this.name = name;
		this.type = type;
		this.size = size;
		this.date = date;
		this.parent = parent;
		this.isFolder = isFolder;
		this.userId = userId;
				
	}
	
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public boolean isFolder() {
		return isFolder;
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

}
