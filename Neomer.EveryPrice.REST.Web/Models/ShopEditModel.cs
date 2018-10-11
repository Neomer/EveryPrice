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

        public IList<TagEditModel> Tags;

		public void ToShop(ref IShop shopModel)
		{
			if (shopModel == null)
			{
				return;
			}

			shopModel.Address = this.Address;

			if (Tags != null)
			{
				shopModel.Tags = Tags
								.Distinct()
								.Where(_ => !String.IsNullOrEmpty(_.Value) && _.Value.Length > 2)
								.Select(_ => TagManager.Instance.FindTag(_.Value) ?? new Tag() { Value = _.Value })
								.ToList<ITag>();
			} else
			{
				shopModel.Tags = null;
			}
        }
    }
}