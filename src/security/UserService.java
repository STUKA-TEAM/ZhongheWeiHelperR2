package security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import security.dao.UserDAO;

@Service
public class UserService implements UserDetailsService {		
	@Override
	public User loadUserByUsername(final String username) 
			throws UsernameNotFoundException {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserDAO userDao = (UserDAO) context.getBean("UserDAO");
		((ConfigurableApplicationContext)context).close();
		
		return userDao.loadUserByUsername(username);
	}
}
