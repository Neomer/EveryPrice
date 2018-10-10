using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class TagController : ApiController
    {
        public FindTagsViewModel Get(string part)
        {
            if (string.IsNullOrEmpty(part))
            {
                return null;
            }

            var tagList = TagManager.Instance.FindTags(part);

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
