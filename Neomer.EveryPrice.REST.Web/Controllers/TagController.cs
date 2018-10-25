using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using Neomer.EveryPrice.SDK.Web.Http;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class TagController : BaseApiController
	{
        public FindTagsViewModel Get(string part)
        {
			var user = SecurityManager.Instance.GetUserByToken(CurrentSession, Request.Headers);
			if (user == null)
			{
				return null;
			}
			if (string.IsNullOrEmpty(part))
            {
                return null;
            }
            var tagList = TagManager.Instance.FindTags(CurrentSession, part);

			var result = new FindTagsViewModel(part);
			result.Tags = tagList == null ? null :
                tagList.Select(_ => new TagViewModel()
                {
                    Uid = _.Uid,
                    Value = _.Value,
                    EntityCount = _.Shops.Count
                })
                .ToList<TagViewModel>();

			return result;
        }
    }
}
