using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{

    [Serializable]
    public class TagViewModel
    {
        public TagViewModel()
        {

        }

        public TagViewModel(ITag tag)
        {
            Uid = tag.Uid;
            Value = tag.Value;
            EntityCount = tag.Shops != null ? tag.Shops.Count : 0;
        }

        public Guid Uid;

        public String Value;

        public int EntityCount;

    }
}