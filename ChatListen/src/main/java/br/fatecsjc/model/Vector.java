package br.fatecsjc.model;

/**
 * @author Luiz Carlos Farias da Silva
 * Class contains model (annotations) and access methods of table vector
 */

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "vector")
@NamedQueries({ @NamedQuery(name = "Vector.findAll", query = "SELECT v FROM Vector v") })
public class Vector implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id") Integer id;

	@Column(name = "posit")
	private Double posit;

	@Column(name = "joyw")
	private Double joyw;

	@Column(name = "sadw")
	private Double sadw;

	@Column(name = "angw")
	private Double angw;

	@Column(name = "surw")
	private Double surw;

	@Column(name = "disw")
	private Double disw;

	@Column(name = "feaw")
	private Double feaw;

	@Column(name = "appw")
	private Double appw;

	@Column(name = "relw")
	private Double relw;

	@Column(name = "famw")
	private Double famw;

	@Column(name = "cdew")
	private Double cdew;

	@Column(name = "infw")
	private Double infw;

	@Column(name = "prpw")
	private Double prpw;

	@Column(name = "refw")
	private Double refw;

	@Column(name = "oblv")
	private Double oblv;

	@Column(name = "emot")
	private Double emot;

	@Column(name = "imps")
	private Double imps;

	@Column(name = "catg")
	private Double catg;

	public Vector() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPosit() {
		return posit;
	}

	public void setPosit(Double posti) {
		this.posit = posti;
	}

	public Double getJoyw() {
		return joyw;
	}

	public void setJoyw(Double joyw) {
		this.joyw = joyw;
	}

	public Double getSadw() {
		return sadw;
	}

	public void setSadw(Double sadw) {
		this.sadw = sadw;
	}

	public Double getAngw() {
		return angw;
	}

	public void setAngw(Double angw) {
		this.angw = angw;
	}

	public Double getSurw() {
		return surw;
	}

	public void setSurw(Double surw) {
		this.surw = surw;
	}

	public Double getDisw() {
		return disw;
	}

	public void setDisw(Double disw) {
		this.disw = disw;
	}

	public Double getFeaw() {
		return feaw;
	}

	public void setFeaw(Double fear) {
		this.feaw = fear;
	}

	public Double getAppw() {
		return appw;
	}

	public void setAppw(Double appw) {
		this.appw = appw;
	}

	public Double getRelw() {
		return relw;
	}

	public void setRelw(Double relw) {
		this.relw = relw;
	}

	public Double getFamw() {
		return famw;
	}

	public void setFamw(Double famw) {
		this.famw = famw;
	}

	public Double getCdew() {
		return cdew;
	}

	public void setCdew(Double cdew) {
		this.cdew = cdew;
	}

	public Double getInfw() {
		return infw;
	}

	public void setInfw(Double infw) {
		this.infw = infw;
	}

	public Double getPrpw() {
		return prpw;
	}

	public void setPrpw(Double prpw) {
		this.prpw = prpw;
	}

	public Double getRefw() {
		return refw;
	}

	public void setRefw(Double refw) {
		this.refw = refw;
	}

	public Double getOblv() {
		return oblv;
	}

	public void setOblv(Double oblv) {
		this.oblv = oblv;
	}

	public Double getEmot() {
		return emot;
	}

	public void setEmot(Double emot) {
		this.emot = emot;
	}

	public Double getImps() {
		return imps;
	}

	public void setImps(Double imps) {
		this.imps = imps;
	}

	public Double getCatg() {
		return catg;
	}

	public void setCatg(Double catg) {
		this.catg = catg;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Vector)) {
			return false;
		}
		Vector other = (Vector) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.fatecsjc.Vector[ id=" + id + " ]";
	}

}
