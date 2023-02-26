package it.epicode.be.forestrycorps.models;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String username;
	private String name;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String password;
	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;	
	
	@Override
	public String toString() {
		return "Id: " + String.format("%1$-"+ 5 + "s", this.getId()) + "Nome: " + String.format("%1$-"+ 18 + "s",this.getName()) 
				+ "UserName: " + String.format("%1$-"+ 13 + "s", this.getUsername()) + "Email: " 
				+ String.format("%1$-"+ 25 + "s", this.getEmail());
	}
}
