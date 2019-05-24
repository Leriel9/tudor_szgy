package hu.elte.szgy.tudor.rest;

import hu.elte.szgy.tudor.data.User;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class TudorUserPrincipal implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private User user;
    private List<GrantedAuthority> auths=new ArrayList<GrantedAuthority>(5);
 
    public TudorUserPrincipal(User user) {
        this.user = user;

        // assign role from usertype field: ROLE_BETEG, ROLE_ORVOS, etc
        auths.add(new SimpleGrantedAuthority("ROLE_" + user.getType().name()));
        
        //if(user.getType() == UserType.UGYFEL) {
        //	auths.add(new SimpleGrantedAuthority("ROLE_UGYFEL"));
        //}
        //if(user.getType() == UserType.TUDOR) {
        //	auths.add(new SimpleGrantedAuthority("ROLE_TUDOR"));
        //}
        //if(user.getType() == UserType.ADMIN) {
        //	auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        //}
    }

	public java.util.Collection<? extends GrantedAuthority> getAuthorities() { return auths; }  
	public java.lang.String getUsername() { return user.getUsername(); }
	public java.lang.String getPassword() { return user.getPassword(); }
	public int getTudorId() { return user.getUserId(); }

	public boolean isEnabled() { return true; }
	public boolean isCredentialsNonExpired() { return true; }
	public boolean isAccountNonExpired() { return true; }
	public boolean isAccountNonLocked() { return true; }
}
