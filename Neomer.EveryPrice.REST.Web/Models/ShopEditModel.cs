using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    [Serializable]
    public class ShopEditModel
    {
        public string Name;

        public double Lat;

        public double Lng;

        public string Address;

        public string Tags;

        public void ToShop(ref IShop shopModel)
        {
            if (shopModel == null)
            {
                return;
            }

            shopModel.Address = this.Address;
            shopModel.Lat = this.Lat;
            shopModel.Lng = this.Lng;
            shopModel.Name = this.Name;
            List<ITag> tagList = shopModel.Tags as List<ITag> ?? new List<ITag>();
            var new_tags = Tags.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries).Distinct();
            foreach (var t in new_tags)
            {
                var tag = TagManager.Instance.FindTag(t);

                if (tag == null)
                {
                    tag = new Tag()
                    {
                        Value = t,
                        Shops = new List<IShop>() { shopModel }
                    };
                }
                tagList.Add(tag);

            }
            shopModel.Tags = tagList;
        }
    }
}