package nemofrl.nemoapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.internal.LinkedTreeMap;

import nemofrl.nemoapi.entity.RespDTO;
import nemofrl.nemoapi.exception.NemoAPIException;
import nemofrl.nemoapi.service.EmojiService;

@RestController
public class EmojiController {

	private static final Logger logger = LogManager.getLogger(EmojiController.class);

	@Autowired
	private EmojiService emojiService;
	
	@RequestMapping(value = "/getEmoji", produces = { "application/json;charset=UTF-8" })
	public RespDTO googleSearch(HttpServletResponse resp,String search,Integer page) {
		RespDTO respDTO=new RespDTO("请求成功",RespDTO.SUCCESS,null);
		resp.setHeader("Access-Control-Allow-Origin", "*");
		if(page==null||page<=0)
			page=1;
		if(StringUtils.isBlank(search)) {
			respDTO.setCode(RespDTO.ERROR_PARAMSLACK);
			respDTO.setMsg("搜索关键字不能为空哦！");
			return respDTO;
		}
		try {
			List<LinkedTreeMap> result=emojiService.getSogoEmoji(search, page);
			respDTO.setData(result);
		} catch (NemoAPIException e) {
			logger.error(e.getMsg(),e.getE());
			respDTO.setCode(e.getCode());
			respDTO.setMsg(e.getMsg());
		}

		return respDTO;
	}
}
