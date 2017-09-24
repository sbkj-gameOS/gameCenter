package com.bradypod.web.handler.apps.business.platform;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bradypod.core.BMDataContext;
import com.bradypod.util.Menu;
import com.bradypod.util.cache.CacheHelper;
import com.bradypod.web.handler.Handler;
import com.bradypod.web.model.AccountConfig;
import com.bradypod.web.model.AiConfig;
import com.bradypod.web.model.BeiMiDic;
import com.bradypod.web.model.GameConfig;
import com.bradypod.web.service.repository.jpa.AccountConfigRepository;
import com.bradypod.web.service.repository.jpa.AiConfigRepository;
import com.bradypod.web.service.repository.jpa.GameConfigRepository;

@Controller
@RequestMapping("/apps/platform")
public class GameConfigController extends Handler{
	
	@Autowired
	private AccountConfigRepository accountRes ;
	
	@Autowired
	private GameConfigRepository gameConfigRes ;
	
	@Autowired
	private AiConfigRepository aiConfigRes ;
	
	@RequestMapping({"/config/account"})
	@Menu(type="platform", subtype="account")
	public ModelAndView account(ModelMap map , HttpServletRequest request){
		List<AccountConfig> accountList = accountRes.findByOrgi(super.getOrgi(request)) ;
		if(accountList.size() > 0){
			map.addAttribute("accountConfig", accountList.get(0)) ;
		}
		return request(super.createAppsTempletResponse("/apps/business/platform/desktop/account"));
	}
	
	@RequestMapping({"/config/account/save"})
	@Menu(type="platform", subtype="account")
	public ModelAndView account(ModelMap map , HttpServletRequest request , @Valid AccountConfig account){
		List<AccountConfig> accountList = accountRes.findByOrgi(super.getOrgi(request)) ;
		if(accountList.size() > 0){
			AccountConfig tempConfig = accountList.get(0) ;
			account.setId(tempConfig.getId());
		}
		account.setOrgi(super.getOrgi(request));
		account.setCreater(super.getUser(request).getId());
		account.setCreatetime(new Date());
		accountRes.save(account) ;
		return request(super.createRequestPageTempletResponse("redirect:/apps/platform/config/account.html"));
	}
	
	
	@RequestMapping({"/config/game"})
	@Menu(type="platform", subtype="gameconfig")
	public ModelAndView content(ModelMap map , HttpServletRequest request){
		List<GameConfig> gameConfigList = gameConfigRes.findByOrgi(super.getOrgi(request)) ;
		if(gameConfigList.size() > 0){
			map.addAttribute("gameConfig", gameConfigList.get(0)) ;
		}
		map.addAttribute("gameModelList", BeiMiDic.getInstance().getDic(BMDataContext.BEIMI_SYSTEM_GAME_TYPE_DIC)) ;
		return request(super.createAppsTempletResponse("/apps/business/platform/config/game"));
	}
	
	@RequestMapping({"/config/game/save"})
	@Menu(type="platform", subtype="gameconfig")
	public ModelAndView game(ModelMap map , HttpServletRequest request , @Valid GameConfig game){
		List<GameConfig> gameConfigList = gameConfigRes.findByOrgi(super.getOrgi(request)) ;
		if(gameConfigList.size() > 0){
			GameConfig tempConfig = gameConfigList.get(0) ;
			game.setId(tempConfig.getId());
		}
		game.setOrgi(super.getOrgi(request));
		game.setCreater(super.getUser(request).getId());
		game.setCreatetime(new Date());
		gameConfigRes.save(game) ;
		CacheHelper.getSystemCacheBean().put(BMDataContext.ConfigNames.GAMECONFIG.toString()+"."+super.getOrgi(request), game, super.getOrgi(request));
		return request(super.createRequestPageTempletResponse("redirect:/apps/platform/config/game.html"));
	}
	
	@RequestMapping({"/config/ai"})
	@Menu(type="platform", subtype="aiconfig")
	public ModelAndView ai(ModelMap map , HttpServletRequest request){
		List<GameConfig> accountList = gameConfigRes.findByOrgi(super.getOrgi(request)) ;
		if(accountList.size() > 0){
			map.addAttribute("accountConfig", accountList.get(0)) ;
		}
		return request(super.createAppsTempletResponse("/apps/business/platform/config/ai"));
	}
	
	@RequestMapping({"/config/ai/save"})
	@Menu(type="platform", subtype="aiconfig")
	public ModelAndView aiconfig(ModelMap map , HttpServletRequest request , @Valid AiConfig ai){
		List<AiConfig> aiConfigList = aiConfigRes.findByOrgi(super.getOrgi(request)) ;
		if(aiConfigList.size() > 0){
			AiConfig tempConfig = aiConfigList.get(0) ;
			ai.setId(tempConfig.getId());
		}
		ai.setOrgi(super.getOrgi(request));
		ai.setCreater(super.getUser(request).getId());
		ai.setCreatetime(new Date());
		aiConfigRes.save(ai) ;
		return request(super.createRequestPageTempletResponse("redirect:/apps/platform/config/ai.html"));
	}
	
}
