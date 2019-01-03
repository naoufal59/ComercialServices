package org.khould.tp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StatisticsData implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long statisticId;
	private Long visitsCount;
	private Long announceVisitCount;
	
	public StatisticsData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public StatisticsData(Long statisticId, Long visitsCount, Long announceVisitCount) {
		super();
		this.statisticId = statisticId;
		this.visitsCount = visitsCount;
		this.announceVisitCount = announceVisitCount;
	}

	public Long getStatisticId() {
		return statisticId;
	}

	public void setStatisticId(Long statisticId) {
		this.statisticId = statisticId;
	}

	public Long getVisitsCount() {
		return visitsCount;
	}

	public void setVisitsCount(Long visitsCount) {
		this.visitsCount = visitsCount;
	}

	public Long getAnnounceVisitCount() {
		return announceVisitCount;
	}

	public void setAnnounceVisitCount(Long announceVisitCount) {
		this.announceVisitCount = announceVisitCount;
	}
	
}
