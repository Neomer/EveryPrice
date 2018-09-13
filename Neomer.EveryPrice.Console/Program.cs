using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.Console
{
    class Program
    {
        static void Main(string[] args)
        {

            var location = System.Reflection.Assembly.GetExecutingAssembly().Location;
            var appPath = System.IO.Path.GetDirectoryName(location);

            NHibernateHelper.Instance.OpenSession(appPath + @"\Nhibernate.cfg.xml");

            {
                var transactionUid = NHibernateHelper.Instance.BeginTransaction();
                IUser adminUser = new User();
                adminUser.Uid = Guid.Parse("AA500E8B-D6FF-4EA6-A9D4-189F1FE74288");
                adminUser.Username = "Admin";

                var userManager = UserManager.Instance as UserManager;
                userManager.RegisterUser(adminUser);

                if (adminUser != null)
                {
                    var adminProfile = new UserProfile();
                    adminProfile.Owner = adminUser;
                    adminProfile.Name = "Admin";
                    adminProfile.BirthDate = DateTime.Parse("1988-07-19");

                    var userProfileManager = UserProfileManager.Instance as UserProfileManager;
                    userProfileManager.Save(adminProfile);
                }

                NHibernateHelper.Instance.CommitTransaction(transactionUid);
            }

            NHibernateHelper.Instance.CloseSession();
        }
    }
}
