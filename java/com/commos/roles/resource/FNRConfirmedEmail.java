package com.commos.roles.resource;

import org.springframework.web.bind.annotation.RequestParam;

public interface FNRConfirmedEmail {

  String confirmedEmail(@RequestParam("email") String email, @RequestParam("token") String token);
}
