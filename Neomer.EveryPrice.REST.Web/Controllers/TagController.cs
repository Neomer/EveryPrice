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
        public List<TagsViewModel> Get(string part)
        {
            if (string.IsNullOrEmpty(part))
            {
                return null;
            }

            var tagList = TagManager.Instance.FindTags(part);

            return tagList.Select(_ => new TagsViewModel()
            {
                Uid = _.Uid,
                Value = _.Value,
                EntityCount = _.Shops.Count
            })
            .ToList<TagsViewModel>();
        }
    }
}
