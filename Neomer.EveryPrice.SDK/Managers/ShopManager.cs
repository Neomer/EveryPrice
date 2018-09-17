﻿using Neomer.EveryPrice.SDK.Core;
using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class ShopManager : BaseEntityManager<ShopManager, IShop>
    {
        protected ShopManager()
        {

        }

        public override void Save(IEntity entity)
        {
            IShop shop = entity as IShop;

            if (shop == null)
            {
                throw new UnsupportedEntityException();
            }

            if (string.IsNullOrEmpty(shop.Name))
            {
                throw new FormatException("Название магазина не может быть пустым!");
            }

            base.Save(entity);
        }

        public IList<Shop> GetShopsNear(Location location, double distance)
        {
            var query = NHibernateHelper.Instance.CurrentSession
                .CreateSQLQuery(String.Format("select * from [EveryPrice].[dbo].[Shops] s where [dbo].[GEO_DISTANCE]( {0}, {1}, s.Lat, s.Lng) < {2};",
                    location.Latitude.ToString().Replace(',', '.'),
                    location.Longtitude.ToString().Replace(',', '.'),
                    distance.ToString().Replace(',', '.')))
                .AddEntity(typeof(Shop));

            return query.List<Shop>();
        }
    }
}
