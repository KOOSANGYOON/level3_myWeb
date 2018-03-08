package net.slipp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;

	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob
	private String contents;

	private LocalDateTime createDate;
	
	@OneToMany(mappedBy="question")
	@OrderBy("createDate DESC")
	private List<Answer> answers;
	
	@JsonProperty
	private Integer countOfAnswer = 0;
	
	public Question() {
	}

	public Question(User writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}

	@JsonIgnore
	public List<Answer> getAnswers() {
		return answers;
	}
	
	public void addAnswer() {
		this.countOfAnswer += 1;
	}
	
	public void deleteAnswer() {
		this.countOfAnswer -= 1;
	} 

	@Override
	public String toString() {
		return "Question [id=" + id + ", writer=" + writer + ", title=" + title + ", contents=" + contents
				+ ", createDate=" + createDate + ", answers=" + answers + "]";
	}

	public long getId() {
		return id;
	}

	public User getWriter() {
		return writer;
	}
	
	public String getContents() {
		return contents;
	}

	public String getTitle() {
		return title;
	}

	public String getFormattedCreateDate() {
		if (createDate == null) {
			return "";
		}
		return createDate.format(DateTimeFormatter.ofPattern("yyyy 년/ MM 월/ dd 일 HH:mm:ss"));
	}
	
	public Integer getCountOfAnswer() {
		return countOfAnswer;
	}

	public Integer getAnswerCount() {
		return answers.size();
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public boolean isSameWriter(User loginUser) {
		return this.writer.equals(loginUser);
	}

}
