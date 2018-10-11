using Neomer.EveryPrice.SDK.Core;
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
        
        public override void SaveIsolate(IEntity entity)
        {
            var shop = entity as IShop;
            if (shop == null)
            {
                throw new UnsupportedEntityException();
            }
            using (var tr = NHibernateHelper.Instance.CurrentSession.BeginTransaction())
            {
				/*
				if (shop.Tags != null)
				{
					foreach (var t in shop.Tags)
					{
						TagManager.Instance.Save(t);
					}
                }
				*/
				Save(shop);
				try
				{
                    tr.Commit();
                }
                catch (Exception ex)
                {
                    tr.Rollback();
                    throw ex;
                }
            }
        }

		public IList<Shop> GetShopsNear(Location location, double distance, ITag tag)
		{
			if (tag == null)
			{
				return GetShopsNear(location, distance);
			}

			var query = NHibernateHelper.Instance.CurrentSession
				.CreateSQLQuery(String.Format("select * from [EveryPrice].[dbo].[Shops] s left join (select s.[Uid], [EveryPrice].[dbo].[GEO_DISTANCE]( {0}, {1}, s.Lat, s.Lng) Distance from [EveryPrice].[dbo].[Shops] s) d on s.[Uid]=d.[Uid] left join [EveryPrice].[dbo].[Tag_Shop] ts on ts.[ShopUid] = s.[Uid] where d.Distance < {2} and ts.[TagUid] = '{3}' order by d.Distance;",
					location.Latitude.ToString().Replace(',', '.'),
					location.Longtitude.ToString().Replace(',', '.'),
					distance.ToString().Replace(',', '.'),
					tag.Uid))
				.AddEntity(typeof(Shop));

			return query.List<Shop>();
		}

		public IList<Shop> GetShopsNear(Location location, double distance)
        {
			if (location == null)
			{
				return null;
			}

			var query = NHibernateHelper.Instance.CurrentSession
                .CreateSQLQuery(String.Format("select s.*, d.Distance from [EveryPrice].[dbo].[Shops] s left join (select s.[Uid], [EveryPrice].[dbo].[GEO_DISTANCE]( {0}, {1}, s.Lat, s.Lng) Distance from [EveryPrice].[dbo].[Shops] s) d on s.[Uid]=d.[Uid] where d.Distance < {2} order by d.Distance;",
                    location.Latitude.ToString().Replace(',', '.'),
                    location.Longtitude.ToString().Replace(',', '.'),
                    distance.ToString().Replace(',', '.')))
                .AddEntity(typeof(Shop));

            return query.List<Shop>();
        }
    }
}
