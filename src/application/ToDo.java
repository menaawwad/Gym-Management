package application;

import java.sql.Date;

public class ToDo {

	private int ID;
	private String Todo;
	private Date date;
	private String Status;

	public ToDo(int iD, String todo, Date date, String status) {
		super();
		ID = iD;
		Todo = todo;
		this.date = date;
		Status = status;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTodo() {
		return Todo;
	}

	public void setTodo(String todo) {
		Todo = todo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "ToDo [ID=" + ID + ", Todo=" + Todo + ", date=" + date + ", Status=" + Status + "]";
	}

}
