package com.example.batch.domain;

public class Batch {
 
private Integer id;
private Integer depth;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getDepth() {
	return depth;
}
public void setDepth(Integer depth) {
	this.depth = depth;
}
@Override
public String toString() {
	return "Batch [id=" + id + ", depth=" + depth + "]";
}



}
